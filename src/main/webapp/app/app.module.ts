import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RamblingsofarationalmanSharedModule } from 'app/shared/shared.module';
import { RamblingsofarationalmanCoreModule } from 'app/core/core.module';
import { RamblingsofarationalmanAppRoutingModule } from './app-routing.module';
import { RamblingsofarationalmanHomeModule } from './home/home.module';
import { RamblingsofarationalmanEntityModule } from './entities/entity.module';
import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRadioModule } from '@angular/material/radio';
import { MatToolbarModule } from '@angular/material/toolbar';

import { FormsModule } from '@angular/forms';
import { MglTimelineModule } from 'angular-mgl-timeline';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    RamblingsofarationalmanSharedModule,
    RamblingsofarationalmanCoreModule,
    RamblingsofarationalmanHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RamblingsofarationalmanEntityModule,
    RamblingsofarationalmanAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class RamblingsofarationalmanAppModule {}
