import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TestappUserProfileModule } from './user-profile/user-profile.module';
import { TestappBusinessModule } from './business/business.module';
import { TestappReviewModule } from './review/review.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        TestappUserProfileModule,
        TestappBusinessModule,
        TestappReviewModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestappEntityModule {}
