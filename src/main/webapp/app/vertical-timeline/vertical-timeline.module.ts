import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxVerticalTimelineModule } from 'ngx-vertical-timeline';
import { VerticalTimelineComponent } from './vertical-timeline.component';

@NgModule({
  declarations: [VerticalTimelineComponent],
  imports: [CommonModule, NgxVerticalTimelineModule],
  exports: [VerticalTimelineComponent],
})
export class VerticalTimelineModule {}
