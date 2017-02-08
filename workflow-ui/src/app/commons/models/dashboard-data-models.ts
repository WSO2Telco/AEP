export class DashboardData {
    appCreationsForUser: number = 0;
    appCreationsForGroup: number = 0;
    totalAppCreations: number = 0;
    subCreationsForUser: number = 0;
    subCreationsForGroup: number = 0;
    totalSubCreations: number = 0;
}

export class DashboardDataRequestParam {
    candidateGroups: string;
    assignee: string;
}

export class HistoryBarGraphData {
    x_axis: string[];
    data: {
        data: number[],
        label: string
    }[] = [];
}
