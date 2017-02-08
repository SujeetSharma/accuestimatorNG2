const enum STATEENUM {
    'DRAFT',
    'INPROGRESS',
    'COMPLETED',
    'READY',
    'INQUEUE',
    'APPROVED'
};


export class Project {
    constructor(
        public id?: string,
        public name?: string,
        public description?: string,
        public active?: boolean,
        public version?: string,
        public state?: STATEENUM,
        public createdby?: string,
        public referencedFrom?: string,
        public createdon?: any,
        public modifiedby?: string,
        public modifiedon?: any,
        public templateId?: string[],
    ) { }
}
