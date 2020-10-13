import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RamblingsOfARationalManSharedModule } from 'app/shared/shared.module';

import { DocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [RamblingsOfARationalManSharedModule, RouterModule.forChild([docsRoute])],
  declarations: [DocsComponent],
})
export class DocsModule {}
