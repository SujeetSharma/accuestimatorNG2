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


export class FactorCategory {
    constructor(
        public id?: string,
        public name?: string,
        public type?: TYPEENUM,
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
