const enum STATEENUM {
    'DRAFT',
    'INPROGRESS',
    'COMPLETED',
    'READY',
    'INQUEUE',
    'APPROVED'
};


export class ProjectTemplateMapping {
    constructor(
        public id?: string,
        public name?: string,
        public description?: string,
        public projectId?: string,
        public templateId?: string,
        public active?: boolean,
        public version?: string,
        public state?: STATEENUM,
        public createdby?: string,
        public createdon?: any,
        public modifiedby?: string,
        public modifiedon?: any,
    ) { }
}
