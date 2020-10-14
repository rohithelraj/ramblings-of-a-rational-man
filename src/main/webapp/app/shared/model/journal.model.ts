import { Moment } from 'moment';
import { JournalType } from 'app/shared/model/enumerations/journal-type.model';

export interface IJournal {
  id?: number;
  title?: string;
  tags?: JournalType;
  journalDate?: Moment;
  text?: string;
}

export class Journal implements IJournal {
  constructor(public id?: number, public title?: string, public tags?: JournalType, public journalDate?: Moment, public text?: string) {}
}
