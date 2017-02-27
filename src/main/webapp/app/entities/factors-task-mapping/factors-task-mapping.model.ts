import { FTEstimates } from './estimates.model';

const enum STATEENUM {
    'DRAFT',
    'INPROGRESS',
    'COMPLETED',
    'READY',
    'INQUEUE',
    'APPROVED'
};

const enum ESTTYPEENUM {
    'PERT',
    'AGILE',
    'HOURS'
};

export class FactorsTaskMapping {
    constructor(
        public id?: string,
        public name?: string,
        public taskCategory?: string,
        public factorCategory?: string,
        public version?: string,
        public state?: STATEENUM,
        public createdby?: string,
        public estimates?: FTEstimates,
        public createdon?: any,
        public modifiedby?: string,
        public modifiedon?: any,
        public description?: string,
        public esttype?:ESTTYPEENUM,
        public active?: boolean,
    ) { }
}
