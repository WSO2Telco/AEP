import {TableDataType} from "./common-data-models";
export class DateTimeInfo {
    date: string;
    time: string;
    offset: string;
    unformatted: string;
}

export class ApplicationTask {
    id: number;
    assignee: string;
    createTime: DateTimeInfo;
    taskDescription: string;
    applicationId: number;
    applicationName: string;
    applicationDescription: string;
    operators: string;
    tier: string;
    allTiers: string[];
    userName: string;
    isModified: boolean;
    status: string;
    comment: string;

    toString() {
        return '' + this.id + ',' + this.applicationName + ',' + this.applicationDescription + ',' + this.comment;
    }
}

export class MetaData {
    order: string;
    size: number;
    sort: string;
    start: number;
    total: number;
}

export class PaginationInfo{
    pageNo:number;
    recordsPerPage : number;

    constructor(pageNo: number,recordsPerPage:number) {
        this.pageNo = pageNo;
        this.recordsPerPage = recordsPerPage;
    }
}

export class ApplicationTaskResult {
    applicationTasks: ApplicationTask[];
    meteData: MetaData
}

type PROCESS_TYPE = 'APPLICATION_CREATION' | 'SUBSCRIPTION_CREATION';

export class ApplicationTaskSearchParam {
    candidateGroups: string;
    processType: PROCESS_TYPE;
    assignee: string;
    start:number;
    size: number
}

export class AssignApplicationTaskParam {
    taskId: number;
    assignee: string;
}

export class ApproveApplicationCreationTaskParam {
    taskId: number;
    taskType: 'application';
    user: string;
    selectedTier: string;
    status: 'APPROVED' | 'REJECTED';
    description: string;

    toString() {
        return this.taskId + ', ' + this.description + ', ' + this.selectedTier + ', ' + this.status;
    }
}

export class ApproveSubscriptionCreationTaskParam {
    taskId: number;
    taskType: 'subscription';
    user: string;
    selectedTier: string;
    status: 'APPROVED' | 'REJECTED';
    description: string;
}

export class ApprovalEvent {
    task: ApplicationTask;
    dataType: TableDataType;
    status: 'APPROVED' | 'REJECTED';

    constructor(task: ApplicationTask, dataType: TableDataType, status?) {
        this.task = task;
        this.dataType = dataType;
        this.status = status;
    }
}

export class ApplicationTaskFilter {
    ids: number[] = [];
    appNames: string[] = [];
    users: string[] = [];
    fromDate: string;
    toDate: string;
    dataType: TableDataType;
    startRecordNumber : number=0;
    numberOfRecordsPerPage:number=0;

    constructor(dataType: TableDataType,recPerPage?:number) {
        this.dataType = dataType;
        this.numberOfRecordsPerPage = recPerPage;
    }
}

