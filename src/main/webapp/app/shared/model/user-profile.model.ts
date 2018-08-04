import { Moment } from 'moment';
import { IBusiness } from 'app/shared/model//business.model';
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

export interface IUserProfile {
    id?: number;
    state?: State;
    city?: string;
    address?: string;
    phone?: string;
    imageContentType?: string;
    image?: any;
    since?: Moment;
    owner?: boolean;
    imagepath?: string;
    userId?: number;
    businesses?: IBusiness[];
    reviews?: IReview[];
}

export class UserProfile implements IUserProfile {
    constructor(
        public id?: number,
        public state?: State,
        public city?: string,
        public address?: string,
        public phone?: string,
        public imageContentType?: string,
        public image?: any,
        public since?: Moment,
        public owner?: boolean,
        public imagepath?: string,
        public userId?: number,
        public businesses?: IBusiness[],
        public reviews?: IReview[]
    ) {
        this.owner = false;
    }
}
