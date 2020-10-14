import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'journal',
        loadChildren: () => import('./journal/journal.module').then(m => m.RamblingsOfARationalManJournalModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class RamblingsOfARationalManEntityModule {}
