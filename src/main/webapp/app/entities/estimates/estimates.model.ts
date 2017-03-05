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


export class Estimates {
    constructor(
        public id?: string,
        public name?: string,
        public description?: string,
        public projectId?: string,
        public estimates?: string,
        public type?: TYPEENUM,
        public value?: number,
        public version?: string,
        public state?: STATEENUM,
        public referencedFrom?: string,
        public createdby?: string,
        public createdon?: any,
        public modifiedby?: string,
        public modifiedon?: any,
        public active?: boolean,
    ) { }
}
