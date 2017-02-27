

export class RegionModel {
    regionId: number;
    name: string;
    customers: CustomerModel[];
}


export class CustomerModel {
    customerId: number;
    name: string;
    currentPeriod: number;
    previousPeriod: number;
}