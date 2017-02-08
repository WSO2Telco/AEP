export class MenuItem {
    id: number;
    route: string;
    name: string;
    iconName: string;
}

export class TableDataType {
    dataCategory: 'USER' | 'GROUP';
    dataType: 'APPLICATION' | 'SUBSCRIPTION';


    constructor(dataCategory,dataType) {
        this.dataCategory = dataCategory;
        this.dataType = dataType;
    }
}


export class User {
    userName: string;
    password: string;
}

export class LoginResponse{
    userName:string;
    isLoggedIn:boolean;
    roles : string[];
}


