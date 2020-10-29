import { Component } from '@angular/core';

@Component({
  selector: 'jhi-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss'],
})
export class TimelineComponent {
  alternate = true;
  toggle = true;
  color = false;
  size = 40;
  expandEnabled = true;
  contentAnimation = true;
  dotAnimation = true;
  side = 'left';

  entries = [
    {
      header: 'header',
      content: 'content',
    },
  ];

  addEntry(): void {
    this.entries.push({
      header: 'header',
      content: 'content',
    });
  }

  removeEntry(): void {
    this.entries.pop();
  }

  onHeaderClick(event: any): void {
    if (!this.expandEnabled) {
      event.stopPropagation();
    }
  }

  onDotClick(event: any): void {
    if (!this.expandEnabled) {
      event.stopPropagation();
    }
  }

  onExpandEntry(expanded: any, index: any): void {
    //console.log(`Expand status of entry #${index} changed to ${expanded}`);
  }

  toggleSide(): void {
    this.side = this.side === 'left' ? 'right' : 'left';
  }
}
