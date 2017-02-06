const enum STATEENUM {
    'DRAFT',
    'INPROGRESS',
    'COMPLETED',
    'READY',
    'INQUEUE',
    'APPROVED'
};


export class FactorsTaskMapping {
    constructor(
        public id?: string,
        public taskCategory?: string,
        public task?: string,
        public factor?: string,
        public factorCategory?: string,
        public formula?: string,
        public value?: number,
        public version?: string,
        public state?: STATEENUM,
        public createdby?: string,
        public createdon?: any,
        public modifiedby?: string,
        public modifiedon?: any,
        public description?: string,
        public active?: boolean,
    ) { }
}
