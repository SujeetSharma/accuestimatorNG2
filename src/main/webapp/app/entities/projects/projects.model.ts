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


export class Projects {
    constructor(
        public id?: string,
        public name?: string,
        public description?: string,
        public type?: TYPEENUM,
        public state?: STATEENUM,
        public createdby?: string,
        public createdon?: any,
        public modifiedby?: string,
        public modifiedon?: any,
        public version?: string,
        public active?: boolean,
    ) { }
}
