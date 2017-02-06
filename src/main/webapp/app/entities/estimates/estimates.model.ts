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
        public project?: string,
        public type?: TYPEENUM,
        public taskCategory?: string,
        public task?: string,
        public factor?: string,
        public factorCategory?: string,
        public formula?: string,
        public value?: number,
        public version?: string,
        public state?: STATEENUM,
        public copiedFrom?: string,
        public createdby?: string,
        public createdon?: any,
        public modifiedby?: string,
        public modifiedon?: any,
        public description?: string,
        public active?: boolean,
    ) { }
}
