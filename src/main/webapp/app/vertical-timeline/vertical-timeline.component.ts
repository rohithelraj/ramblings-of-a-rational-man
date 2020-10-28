import { Component, OnInit } from '@angular/core';
import { TimelineItem } from 'ngx-vertical-timeline';

@Component({
  selector: 'jhi-vertical-timeline',
  templateUrl: './vertical-timeline.component.html',
  styleUrls: ['./vertical-timeline.component.scss'],
})
export class VerticalTimelineComponent implements OnInit {
  items: TimelineItem[] = [];
  externalVariable = 'hello';
  constructor() {}

  ngOnInit(): void {
    const self = this;

    this.items.push({
      label: 'Action',
      icon: 'fa fa-calendar-plus-o',
      styleClass: 'teste',
      color: '#3498db',
      content: `Lorem ipsum dolor sit amet, consectetur adipiscing elit,
      sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.`,
      title: '18 de June, 2019, 10:12',
      command() {
        alert(`test: ${self.externalVariable}`);
      },
    });

    this.items.push({
      label: 'Action',
      icon: 'fa fa-plus',
      styleClass: 'teste',
      color: '#3498db',
      content: `Ut enim ad minim veniam, quis nostrud exercitation ullamco
      laboris nisi ut aliquip ex ea commodo consequat.`,
      title: '11 de November, 2019, 12:00',
      command() {
        alert('Action!');
      },
    });

    this.items.push({
      label: 'Action',
      icon: 'fa fa-user-circle-o',
      styleClass: 'teste',
      color: '#3498db',
      content: `Duis aute irure dolor in reprehenderit in voluptate velit
       esse cillum dolore eu fugiat nulla pariatur.`,
      title: '01 de December, 2019, 10:12',
      command() {
        alert('Action!');
      },
    });

    this.items.push({
      label: 'Action',
      icon: 'fa fa-handshake-o',
      styleClass: 'teste',
      color: '#3498db',
      content: `Excepteur sint occaecat cupidatat non proident,
      sunt in culpa qui officia deserunt mollit anim id est laborum.`,
      title: '27 de January, 2020, 10:35',
      command() {
        alert('Action!');
      },
    });
    this.items.push({
      label: 'Action',
      icon: 'fa fa-handshake-o',
      styleClass: 'teste',
      color: '#3498db',
      content: `Excepteur sint occaecat cupidatat non proident,
      sunt in culpa qui officia deserunt mollit anim id est laborum.`,
      title: '27 de January, 2020, 10:35',
      command() {
        alert('Action!');
      },
    });
  }
}
