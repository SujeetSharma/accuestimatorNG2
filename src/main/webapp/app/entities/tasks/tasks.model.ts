const enum TYPEENUM {
    'TECHNICAL',
    'MANAGEMENT',
    'CODING',
    'TESTING',
    'DEPLOYMENT',
    'REVIEW',
    'REWORK',
    'OTHERS'
};
const enum STATEENUM {
    'DRAFT',
    'INPROGRESS',
    'COMPLETED',
    'READY',
    'INQUEUE',
    'APPROVED'
};


export class Tasks {
    constructor(
        public id?: string,
        public name?: string,
        public category?: string,
        public type?: TYPEENUM,
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
