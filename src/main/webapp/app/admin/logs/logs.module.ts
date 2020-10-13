import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RamblingsOfARationalManSharedModule } from 'app/shared/shared.module';

import { LogsComponent } from './logs.component';

import { logsRoute } from './logs.route';

@NgModule({
  imports: [RamblingsOfARationalManSharedModule, RouterModule.forChild([logsRoute])],
  declarations: [LogsComponent],
})
export class LogsModule {}
