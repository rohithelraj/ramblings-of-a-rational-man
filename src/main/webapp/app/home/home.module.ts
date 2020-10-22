import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RamblingsofarationalmanSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { MenuModule } from '@kinect-pro/ngp-radial-menu';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
@NgModule({
  imports: [RamblingsofarationalmanSharedModule, RouterModule.forChild([HOME_ROUTE]), MenuModule, FontAwesomeModule],
  declarations: [HomeComponent],
})
export class RamblingsofarationalmanHomeModule {}
