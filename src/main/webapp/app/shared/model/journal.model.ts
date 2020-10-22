import { Moment } from 'moment';
import { journalType } from 'app/shared/model/enumerations/journal-type.model';

export interface IJournal {
  id?: number;
  title?: string;
  tags?: journalType;
  journalDate?: Moment;
  text?: string;
}

export class Journal implements IJournal {
  constructor(public id?: number, public title?: string, public tags?: journalType, public journalDate?: Moment, public text?: string) {}
}
