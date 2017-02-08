import {Injectable, Inject} from '@angular/core';
import {Headers, RequestOptions, Response, Http} from "@angular/http";
import {Observable, Subject, BehaviorSubject} from "rxjs";
import {
    ApplicationTask, ApplicationTaskSearchParam,
    AssignApplicationTaskParam, ApproveApplicationCreationTaskParam, ApproveSubscriptionCreationTaskParam,
    ApplicationTaskFilter, ApplicationTaskResult, PaginationInfo
} from "../commons/models/application-data-models";
import {AuthenticationService} from "../commons/services/authentication.service";
import {SlimLoadingBarService} from "ng2-slim-loading-bar";
import {MessageService} from "../commons/services/message.service";

@Injectable()
export class ApprovalRemoteDataService {

    /**
     * Application Creations assigned to USER Stream
     * @type {BehaviorSubject<ApplicationTask[]>}
     */
    public MyApplicationCreationTasksProvider: Subject<ApplicationTaskResult> = new BehaviorSubject<ApplicationTaskResult>(null);

    /**
     * Application Creations assigned to GROUP user belongs to
     * @type {BehaviorSubject<ApplicationTask[]>}
     */
    public GroupApplicationCreationTasksProvider: Subject<ApplicationTaskResult> = new BehaviorSubject<ApplicationTaskResult>(null);

    /**
     * Application Api subscription creations assigned to USER Stream
     * @type {BehaviorSubject<ApplicationTask[]>}
     */
    public MySubscriptionTasksProvider: Subject<ApplicationTaskResult> = new BehaviorSubject<ApplicationTaskResult>(null);

    /**
     * Application Api subscription creations assigned to GROUP Stream
     * @type {BehaviorSubject<ApplicationTask[]>}
     */
    public GroupSubscriptionTasksProvider: Subject<ApplicationTaskResult> = new BehaviorSubject<ApplicationTaskResult>(null);


    private modifiedApplicationTaskIDs: number[] = new Array();

    private headers: Headers = new Headers({'Content-Type': 'application/json'});

    private options: RequestOptions = new RequestOptions({headers: this.headers});

    private apiEndpoints: Object = {
        search: this.apiContext + '/applications/search',
        assign: this.apiContext + '/applications/assign',
        approveApplicationCreation: this.apiContext + '/applications/approve/application/creation',
        approveSubscriptionCreation: this.apiContext + '/applications/approve/subscription/creation'
    };

    private actionMap = {
        USER: {
            APPLICATION: this.getUserApplicationTasks,
            SUBSCRIPTION: this.getUserAppSubscriptionTasks
        },
        GROUP: {
            APPLICATION: this.getUserGroupApplicationTasks,
            SUBSCRIPTION: this.getUserGroupAppSubscriptionTask
        }
    };

    constructor(private http: Http,
                @Inject('API_CONTEXT') private apiContext: string,
                private slimLoadingBarService: SlimLoadingBarService,
                private message: MessageService,
                private authService: AuthenticationService) {
    }

    private updateModifiedTask(result: ApplicationTask[], modified: number[]) {
        if (!!result) {
            return result.map((task: ApplicationTask) => {
                task.isModified = modified.indexOf(task.id) >= 0 ? true : false;
                return task;
            }).sort((taskOne, taskTwo) => {
                if (taskOne.isModified && !taskTwo.isModified) {
                    return -1;
                }

                if (!taskOne.isModified && taskTwo.isModified) {
                    return 1;
                }

                return 0;
            });
        } else {
            return [];
        }
    }

    private getFilteredObservable(appTask: ApplicationTask[], filter: ApplicationTaskFilter): ApplicationTask[] {
        if (appTask && filter) {
            return appTask
                .filter((task: ApplicationTask) => filter.ids.length == 0 || filter.ids.indexOf(task.id) >= 0)
                .filter((task: ApplicationTask) => filter.appNames.length == 0 || filter.appNames.indexOf(task.applicationName) >= 0)
                .filter((task: ApplicationTask) => filter.users.length == 0 || filter.users.indexOf(task.userName) >= 0)
                .reduce((acc, curr) => {
                    acc.push(curr);
                    return acc;
                }, []);
        } else {
            return appTask;
        }
    }


    getUserApplicationTasks(filter?: ApplicationTaskFilter): void {
        let loginInfo = this.authService.loginUserInfo.getValue();
        if (!!loginInfo) {
            const param: ApplicationTaskSearchParam = {
                assignee: loginInfo.userName,
                size: 100,
                start: 0,
                processType: "APPLICATION_CREATION",
                candidateGroups: null
            };

            if (!!filter) {
                param.size = filter.numberOfRecordsPerPage;
                param.start = filter.startRecordNumber;
            }

            this.slimLoadingBarService.start();

            this.http.post(this.apiEndpoints['search'], param, this.options)
                .map((response: Response) => response.json())
                .subscribe(
                    (result: ApplicationTaskResult) => {
                        if (!!filter) {
                            result.applicationTasks = this.getFilteredObservable(result.applicationTasks, filter);
                        }

                        result.applicationTasks = this.updateModifiedTask(result.applicationTasks, this.modifiedApplicationTaskIDs);

                        this.MyApplicationCreationTasksProvider.next(result);
                    },
                    (error: Response) => {
                        this.slimLoadingBarService.stop();
                        return Observable.throw(error.json().message)
                    },
                    () => {
                        this.slimLoadingBarService.complete();
                    }
                );
        }
    }


    getUserGroupApplicationTasks(filter?: ApplicationTaskFilter): void {
        let loginInfo = this.authService.loginUserInfo.getValue();
        if (!!loginInfo) {
            const param: ApplicationTaskSearchParam = {
                assignee: null,
                processType: "APPLICATION_CREATION",
                size: 100,
                start: 0,
                candidateGroups: loginInfo.roles.toString()
            };

            if (!!filter) {
                param.size = filter.numberOfRecordsPerPage;
                param.start = filter.startRecordNumber;
            }


            this.slimLoadingBarService.start();

            this.http.post(this.apiEndpoints['search'], param, this.options)
                .map((response: Response) => response.json())
                .subscribe(
                    (result: ApplicationTaskResult) => {
                        if (!!filter) {
                            result.applicationTasks = this.getFilteredObservable(result.applicationTasks, filter);
                        }

                        result.applicationTasks = this.updateModifiedTask(result.applicationTasks, this.modifiedApplicationTaskIDs);

                        this.GroupApplicationCreationTasksProvider.next(result)
                    },
                    (error: Response) => Observable.throw(error.json().message),
                    () => {
                        this.slimLoadingBarService.complete();
                    }
                );
        }
    }

    getUserAppSubscriptionTasks(filter?: ApplicationTaskFilter): void {
        let loginInfo = this.authService.loginUserInfo.getValue();
        if (!!loginInfo) {
            const param: ApplicationTaskSearchParam = {
                assignee: loginInfo.userName,
                size: 100,
                start: 0,
                processType: "SUBSCRIPTION_CREATION",
                candidateGroups: null
            };

            if (!!filter) {
                param.size = filter.numberOfRecordsPerPage;
                param.start = filter.startRecordNumber;
            }


            this.slimLoadingBarService.start();

            this.http.post(this.apiEndpoints['search'], param, this.options)
                .map((response: Response) => response.json())
                .subscribe(
                    (result: ApplicationTaskResult) => {
                        if (!!filter) {
                            result.applicationTasks = this.getFilteredObservable(result.applicationTasks, filter);
                        }
                        result.applicationTasks = this.updateModifiedTask(result.applicationTasks, this.modifiedApplicationTaskIDs);

                        this.MySubscriptionTasksProvider.next(result);
                    },
                    (error: Response) => Observable.throw(error.json().message),
                    () => {
                        this.slimLoadingBarService.complete();
                    }
                );
        }
    }


    getUserGroupAppSubscriptionTask(filter?: ApplicationTaskFilter): void {
        let loginInfo = this.authService.loginUserInfo.getValue();
        if (!!loginInfo) {
            const param: ApplicationTaskSearchParam = {
                assignee: null,
                size: 100,
                start: 0,
                processType: "SUBSCRIPTION_CREATION",
                candidateGroups: loginInfo.roles.toString()
            };

            if (!!filter) {
                param.size = filter.numberOfRecordsPerPage;
                param.start = filter.startRecordNumber;
            }


            this.slimLoadingBarService.start();

            this.http.post(this.apiEndpoints['search'], param, this.options)
                .map((response: Response) => response.json())
                .subscribe(
                    (result: ApplicationTaskResult) => {
                        if (!!filter) {
                            result.applicationTasks = this.getFilteredObservable(result.applicationTasks, filter);
                        }
                        result.applicationTasks = this.updateModifiedTask(result.applicationTasks, this.modifiedApplicationTaskIDs);

                        this.GroupSubscriptionTasksProvider.next(result);
                    },
                    (error: Response) => Observable.throw(error.json().message),
                    () => {
                        this.slimLoadingBarService.complete();
                    }
                );

        }
    }

    assignApplicationTaskToUser(taskId) {
        let loginInfo = this.authService.loginUserInfo.getValue();
        if (!!loginInfo) {
            const param = new AssignApplicationTaskParam();
            param.assignee = loginInfo.userName;
            param.taskId = taskId;

            return this.http.post(this.apiEndpoints['assign'], param, this.options)
                .map((response: Response) => {
                    this.modifiedApplicationTaskIDs.push(taskId);
                    return response.json();
                })
                .catch((error: Response) => Observable.throw(error.json().message))
        }
    }

    getModifiedTaskIds(): number[] {
        return this.modifiedApplicationTaskIDs;
    }

    approveApplicationCreationTask(param: ApproveApplicationCreationTaskParam): Observable<any> {
        return this.http.post(this.apiEndpoints['approveApplicationCreation'], param, this.options)
            .map((response: Response) => response.json())
            .catch((error: Response) => Observable.throw(error.json().message))
    }

    approveSubscriptionCreationTask(param: ApproveSubscriptionCreationTaskParam): Observable<any> {
        return this.http.post(this.apiEndpoints['approveSubscriptionCreation'], param, this.options)
            .map((response: Response) => response.json())
            .catch((error: Response) => Observable.throw(error.json().message))
    }

    getAllTasks(): void {
        this.getUserApplicationTasks();
        this.getUserAppSubscriptionTasks();
        this.getUserGroupApplicationTasks();
        this.getUserGroupAppSubscriptionTask();
    }

    getFilteredResult(filter: ApplicationTaskFilter): void {
        this.actionMap[filter.dataType.dataCategory][filter.dataType.dataType] && this.actionMap[filter.dataType.dataCategory][filter.dataType.dataType].call(this, filter);
    }

}
