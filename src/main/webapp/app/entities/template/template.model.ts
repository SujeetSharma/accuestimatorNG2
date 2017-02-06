const enum STATEENUM {
    'DRAFT',
    'INPROGRESS',
    'COMPLETED',
    'READY',
    'INQUEUE',
    'APPROVED'
};


export class Template {
    constructor(
        public id?: string,
        public active?: boolean,
        public factorTaskId?: string,
        public version?: string,
        public state?: STATEENUM,
        public createdby?: string,
        public createdon?: any,
        public referencedFrom?: string,
        public modifiedby?: string,
        public modifiedon?: any,
        public description?: string,
    ) { }
}
