import { Moment } from 'moment';

export interface IReview {
    id?: number;
    title?: string;
    description?: string;
    like?: number;
    dislike?: number;
    date?: Moment;
    imageContentType?: string;
    image?: any;
    videoContentType?: string;
    video?: any;
    imagepath?: string;
    videopath?: string;
    userProfileId?: number;
    businessId?: number;
}

export class Review implements IReview {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public like?: number,
        public dislike?: number,
        public date?: Moment,
        public imageContentType?: string,
        public image?: any,
        public videoContentType?: string,
        public video?: any,
        public imagepath?: string,
        public videopath?: string,
        public userProfileId?: number,
        public businessId?: number
    ) {}
}
