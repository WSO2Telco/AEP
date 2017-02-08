import {Injectable, Inject} from '@angular/core';
import {Headers, RequestOptions, Http, Response} from "@angular/http";
import {Subject, BehaviorSubject, Observable} from "rxjs";
import {MessageService} from "../commons/services/message.service";
import {SlimLoadingBarService} from "ng2-slim-loading-bar";
import {
    ApprovalHistory, ApprovalHistoryFilter, ApprovalHistoryDataset,
    Application
} from "../commons/models/reporing-data-models";

@Injectable()
export class ReportingRemoteDataService {

    /**
     * Subscribers stream
     * @type {BehaviorSubject<string[]>}
     */
    public SubscribersProvider: Subject<string[]> = new BehaviorSubject<string[]>([]);

    /**
     * Operators Stream
     * @type {BehaviorSubject<string[]>}
     */
    public OperatorsProvider:Subject<string[]> = new BehaviorSubject<string[]>([]);

    /**
     * Applications Stream
     * @type {BehaviorSubject<any[]>}
     */
    public ApplicationsProvider:Subject<Application[]> = new BehaviorSubject<Application[]>([])

    /**
     * Approval History stream
     * @type {BehaviorSubject<ApprovalHistory[]>}
     */
    public ApprovalHistoryProvider:Subject<ApprovalHistoryDataset> = new BehaviorSubject<ApprovalHistoryDataset>(null);

    private headers: Headers = new Headers({'Content-Type': 'application/json'});

    private options: RequestOptions = new RequestOptions({headers: this.headers});

    private apiEndpoints: Object = {
        subscribers: this.apiContext + '/reports/subscribers',
        operators : this.apiContext + '/reports/operators',
        approvalHistory : this.apiContext + '/reports/approval',
        applications : this.apiContext + '/reports/applications',
    };

    constructor(@Inject('API_CONTEXT') private apiContext: string,
                private http: Http,
                private message: MessageService,
                private slimLoadingBarService: SlimLoadingBarService) {
    }

    getSubscribers() {
        this.slimLoadingBarService.start();
        this.http.get(this.apiEndpoints['subscribers'], this.options)
            .map((response: Response) => response.json())
            .subscribe(
                (subscribers) => {
                    this.SubscribersProvider.next(subscribers)
                },
                (error) => {
                    this.message.error(error);
                    this.slimLoadingBarService.complete();
                },
                () => {
                    this.slimLoadingBarService.complete()
                }
            )
    }

    getOperators(){
        this.slimLoadingBarService.start();
        this.http.get(this.apiEndpoints['operators'], this.options)
            .map((response: Response) => response.json())
            .subscribe(
                (operators) => {
                    this.OperatorsProvider.next(operators)
                },
                (error) => {
                    this.message.error(error);
                    this.slimLoadingBarService.complete();
                },
                () => {
                    this.slimLoadingBarService.complete()
                }
            )
    }

    getApplicationsBySubscriber(subscriber:string){
        if(!!subscriber){
            this.slimLoadingBarService.start();
            this.http.get(this.apiEndpoints['applications']+'/'+subscriber, this.options)
                .map((response: Response) => response.json())
                .subscribe(
                    (applications:Application[]) => {
                        this.ApplicationsProvider.next(applications)
                    },
                    (error) => {
                        this.message.error(error);
                        this.slimLoadingBarService.complete();
                    },
                    () => {
                        this.slimLoadingBarService.complete()
                    }
                )
        }else{
            this.ApplicationsProvider.next([]);
        }

    }

    getApprovalHistory(approvalHistoryFilter:ApprovalHistoryFilter){
        let filter:ApprovalHistoryFilter = Object.assign({},approvalHistoryFilter);
        this.slimLoadingBarService.start();

        if(!!!filter.subscriber){
            filter.subscriber = '__ALL__';
        }

        if(!!!filter.operator){
            filter.operator = '__ALL__';
        }


        this.http.post(this.apiEndpoints['approvalHistory'], filter,this.options)
            .map((response: Response) => response.json())
            .flatMap((res)=>{return Observable.from(res)})
            .reduce((arr:ApprovalHistoryDataset,cur)=>{
                if(cur.length == 1){
                    arr.noOfRecords = cur[0];
                }else{
                    let tmp:ApprovalHistory = new ApprovalHistory();
                    tmp.applicationId = cur[0];
                    tmp.applicationName= cur[1];
                    tmp.applicationDescription= cur[2];
                    tmp.status= cur[3];
                    tmp.approvedOn = cur[4];
                    arr.recordsCol.push(tmp);
                }
                return arr;
            },new ApprovalHistoryDataset())
            .subscribe(
                (approvalHistory) => {
                    this.ApprovalHistoryProvider.next(approvalHistory)
                },
                (error) => {
                    this.message.error(error);
                    this.slimLoadingBarService.complete();
                },
                () => {
                    this.slimLoadingBarService.complete();
                }
            )
    }

}
