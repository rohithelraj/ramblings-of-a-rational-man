import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IJournal } from 'app/shared/model/journal.model';
import { AngularEditorConfig } from '@kolkov/angular-editor';

@Component({
  selector: 'jhi-journal-detail',
  templateUrl: './journal-detail.component.html',
})
export class JournalDetailComponent implements OnInit {
  journal: IJournal | null = null;
  config: AngularEditorConfig = {
    editable: false,
    spellcheck: true,
    height: '15rem',
    minHeight: '5rem',
    placeholder: 'Enter text here...',
    translate: 'no',
    defaultParagraphSeparator: 'p',
    defaultFontName: 'Arial',
    toolbarHiddenButtons: [['bold']],
    customClasses: [
      {
        name: 'quote',
        class: 'quote',
      },
      {
        name: 'redText',
        class: 'redText',
      },
      {
        name: 'titleText',
        class: 'titleText',
        tag: 'h1',
      },
    ],
  };
  dateDetail: any;
  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journal }) => {
      this.journal = journal;
      if (this.journal != null) {
        if (this.journal.journalDate != null) {
          this.dateDetail = this.journal.journalDate.format(DATE_TIME_FORMAT);
        }
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
