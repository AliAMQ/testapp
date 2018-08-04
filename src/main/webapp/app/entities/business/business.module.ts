import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestappSharedModule } from 'app/shared';
import {
    BusinessComponent,
    BusinessDetailComponent,
    BusinessUpdateComponent,
    BusinessDeletePopupComponent,
    BusinessDeleteDialogComponent,
    businessRoute,
    businessPopupRoute
} from './';

const ENTITY_STATES = [...businessRoute, ...businessPopupRoute];

@NgModule({
    imports: [TestappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BusinessComponent,
        BusinessDetailComponent,
        BusinessUpdateComponent,
        BusinessDeleteDialogComponent,
        BusinessDeletePopupComponent
    ],
    entryComponents: [BusinessComponent, BusinessUpdateComponent, BusinessDeleteDialogComponent, BusinessDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestappBusinessModule {}
