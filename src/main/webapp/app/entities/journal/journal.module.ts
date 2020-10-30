import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { RamblingsofarationalmanSharedModule } from 'app/shared/shared.module';
import { JournalComponent } from './journal.component';
import { JournalDetailComponent } from './journal-detail.component';
import { JournalUpdateComponent } from './journal-update.component';
import { JournalDeleteDialogComponent } from './journal-delete-dialog.component';
import { journalRoute } from './journal.route';
import { TimelineModule } from '../../timeline/timeline.module';
import { VerticalTimelineModule } from '../../vertical-timeline/vertical-timeline.module';
import { MatTabsModule } from '@angular/material/tabs';

@NgModule({
  imports: [
    RamblingsofarationalmanSharedModule,
    RouterModule.forChild(journalRoute),
    AngularEditorModule,
    TimelineModule,
    VerticalTimelineModule,
    MatTabsModule,
  ],
  declarations: [JournalComponent, JournalDetailComponent, JournalUpdateComponent, JournalDeleteDialogComponent],
  entryComponents: [JournalDeleteDialogComponent],
})
export class RamblingsofarationalmanJournalModule {}
