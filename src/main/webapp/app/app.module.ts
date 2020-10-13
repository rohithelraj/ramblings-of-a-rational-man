import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RamblingsOfARationalManSharedModule } from 'app/shared/shared.module';
import { RamblingsOfARationalManCoreModule } from 'app/core/core.module';
import { RamblingsOfARationalManAppRoutingModule } from './app-routing.module';
import { RamblingsOfARationalManHomeModule } from './home/home.module';
import { MenuModule } from '@kinect-pro/ngp-radial-menu';
import { RamblingsOfARationalManEntityModule } from './entities/entity.module';
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
    RamblingsOfARationalManSharedModule,
    MenuModule,
    RamblingsOfARationalManCoreModule,
    RamblingsOfARationalManHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RamblingsOfARationalManEntityModule,
    RamblingsOfARationalManAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class RamblingsOfARationalManAppModule {}
