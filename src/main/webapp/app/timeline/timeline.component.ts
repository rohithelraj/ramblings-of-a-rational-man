import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss'],
})
export class TimelineComponent {
  alternate: boolean = true;
  toggle: boolean = true;
  color: boolean = false;
  size: number = 40;
  expandEnabled: boolean = true;
  contentAnimation: boolean = true;
  dotAnimation: boolean = true;
  side = 'left';

  entries = [
    {
      header: 'header',
      content: 'content',
    },
  ];

  addEntry() {
    this.entries.push({
      header: 'header',
      content: 'content',
    });
  }

  removeEntry() {
    this.entries.pop();
  }

  onHeaderClick(event: any) {
    if (!this.expandEnabled) {
      event.stopPropagation();
    }
  }

  onDotClick(event: any) {
    if (!this.expandEnabled) {
      event.stopPropagation();
    }
  }

  onExpandEntry(expanded: any, index: any) {
    console.log(`Expand status of entry #${index} changed to ${expanded}`);
  }

  toggleSide() {
    this.side = this.side === 'left' ? 'right' : 'left';
  }
}
