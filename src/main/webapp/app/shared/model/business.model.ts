import { Moment } from 'moment';
import { IReview } from 'app/shared/model//review.model';

export const enum State {
    ALBORZ = 'ALBORZ',
    ARDABIL = 'ARDABIL',
    AZERBAIJAN_EAST = 'AZERBAIJAN_EAST',
    AZERBAIJAN_WEST = 'AZERBAIJAN_WEST',
    BUSHEHR = 'BUSHEHR',
    CHAHAR_MAHAAL_AND_BAKHTIARI = 'CHAHAR_MAHAAL_AND_BAKHTIARI',
    FARS = 'FARS',
    GILAN = 'GILAN',
    GOLESTAN = 'GOLESTAN',
    HAMADAN = 'HAMADAN',
    HORMOZGAN = 'HORMOZGAN',
    ILAM = 'ILAM',
    ISFAHAN = 'ISFAHAN',
    KERMAN = 'KERMAN',
    KERMANSHAH = 'KERMANSHAH',
    KHORASAN_NORTH = 'KHORASAN_NORTH',
    KHORASAN_RAZAVI = 'KHORASAN_RAZAVI',
    KHORASAN_SOUTH = 'KHORASAN_SOUTH',
    KHUZESTAN = 'KHUZESTAN',
    KOHGILUYEH_AND_BOYER_AHMAD = 'KOHGILUYEH_AND_BOYER_AHMAD',
    KURDISTAN = 'KURDISTAN',
    LORESTAN = 'LORESTAN',
    MARKAZI = 'MARKAZI',
    MAZANDARAN = 'MAZANDARAN',
    QAZVIN = 'QAZVIN',
    QOM = 'QOM',
    SEMNAN = 'SEMNAN',
    SISTAN_AND_BALUCHESTAN = 'SISTAN_AND_BALUCHESTAN',
    TEHRAN = 'TEHRAN',
    YAZD = 'YAZD',
    ZANJAN = 'ZANJAN'
}

export interface IBusiness {
    id?: number;
    title?: string;
    description?: string;
    state?: State;
    address?: string;
    phone?: string;
    rate?: number;
    since?: Moment;
    link?: string;
    reservation?: boolean;
    delivery?: boolean;
    wifi?: boolean;
    imageContentType?: string;
    image?: any;
    videoContentType?: string;
    video?: any;
    paid?: boolean;
    imagepath?: string;
    videopath?: string;
    userProfileId?: number;
    reviews?: IReview[];
}

export class Business implements IBusiness {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public state?: State,
        public address?: string,
        public phone?: string,
        public rate?: number,
        public since?: Moment,
        public link?: string,
        public reservation?: boolean,
        public delivery?: boolean,
        public wifi?: boolean,
        public imageContentType?: string,
        public image?: any,
        public videoContentType?: string,
        public video?: any,
        public paid?: boolean,
        public imagepath?: string,
        public videopath?: string,
        public userProfileId?: number,
        public reviews?: IReview[]
    ) {
        this.reservation = false;
        this.delivery = false;
        this.wifi = false;
        this.paid = false;
    }
}
