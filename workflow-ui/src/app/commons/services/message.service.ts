import {Injectable} from '@angular/core';
import {ToastyService, ToastOptions, ToastyConfig, toastyServiceFactory} from "ng2-toasty";

@Injectable()
export class MessageService {

    constructor(private toast: ToastyService) {
    }

    private toastOptions: ToastOptions = {
        title: '',
        msg: '',
        showClose: true,
        timeout: 7000,
        theme: 'material'
    };

    public APPROVAL_MESSAGES = {
        APPLICATION_CREATION_ASSIGN_SUCCESS : 'Application creation task successfully assigned',
        SUBSCRIPTION_CREATION_ASSIGN_SUCCESS : 'Subscription creation task successfully assigned',
        APP_CREATION_APPROVE_SUCCESS : 'Application successfully approved',
        APP_CREATION_REJECT_SUCCESS : 'Application successfully rejected',
        APP_SUBSCRIPTION_APPROVE_SUCCESS : 'Application subscription successfully approved',
        APP_SUBSCRIPTION_REJECT_SUCCESS : 'Application subscription successfully rejected'
    };


    success(message: string, title?: string) {
        this.toast.success(Object.assign({}, this.toastOptions, {title, msg: message}));
    }

    error(message: string, title?: string) {
        this.toast.error(Object.assign({}, this.toastOptions, {title, msg: message}));
    }

    warning(message: string, title?: string) {
        this.toast.warning(Object.assign({}, this.toastOptions, {title, msg: message}));
    }

    info(message: string, title?: string) {
        this.toast.info(Object.assign({}, this.toastOptions, {title, msg: message}));
    }

}
