const enum STATEENUM {
    'DRAFT',
    'INPROGRESS',
    'COMPLETED',
    'READY',
    'INQUEUE',
    'APPROVED'
};


export class ProjectUserMapping {
    constructor(
        public id?: string,
        public userid?: string,
        public projectid?: string,
        public active?: boolean,
        public version?: string,
        public state?: STATEENUM,
        public createdby?: string,
        public createdon?: any,
        public modifiedby?: string,
        public modifiedon?: any,
    ) { }
}
