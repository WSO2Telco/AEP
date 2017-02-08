import {Injectable} from '@angular/core';
import {ApplicationTask} from "../commons/models/application-data-models";
import {ApprovalRemoteDataService} from "../data-providers/approval-remote-data.service";

@Injectable()
export class DashboardHelperService {

    constructor(private approvalService: ApprovalRemoteDataService) {
    }

    updateModifiedApplications(applications: ApplicationTask[]): ApplicationTask[] {
        let ids = this.approvalService.getModifiedTaskIds();

        let reduced = applications.reduce((acc, cuu) => {
            if (ids.indexOf(cuu.id) >= 0) {
                cuu.isModified = true;
                acc.modified.push(cuu);
            } else {
                acc.unmodified.push(cuu);

            }
            return acc;
        }, {modified: [], unmodified: []});

        return [].concat(reduced.modified, reduced.unmodified);
    }

}
