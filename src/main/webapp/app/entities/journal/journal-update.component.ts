import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IJournal, Journal } from 'app/shared/model/journal.model';
import { JournalService } from './journal.service';

@Component({
  selector: 'jhi-journal-update',
  templateUrl: './journal-update.component.html',
})
export class JournalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    tags: [null, [Validators.required]],
    journalDate: [null, [Validators.required]],
    text: [null, [Validators.required]],
  });

  constructor(protected journalService: JournalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journal }) => {
      if (!journal.id) {
        const today = moment().startOf('day');
        journal.journalDate = today;
      }

      this.updateForm(journal);
    });
  }

  updateForm(journal: IJournal): void {
    this.editForm.patchValue({
      id: journal.id,
      title: journal.title,
      tags: journal.tags,
      journalDate: journal.journalDate ? journal.journalDate.format(DATE_TIME_FORMAT) : null,
      text: journal.text,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const journal = this.createFromForm();
    if (journal.id !== undefined) {
      this.subscribeToSaveResponse(this.journalService.update(journal));
    } else {
      this.subscribeToSaveResponse(this.journalService.create(journal));
    }
  }

  private createFromForm(): IJournal {
    return {
      ...new Journal(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      tags: this.editForm.get(['tags'])!.value,
      journalDate: this.editForm.get(['journalDate'])!.value
        ? moment(this.editForm.get(['journalDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      text: this.editForm.get(['text'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJournal>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
