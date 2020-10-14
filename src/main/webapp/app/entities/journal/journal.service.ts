import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJournal } from 'app/shared/model/journal.model';

type EntityResponseType = HttpResponse<IJournal>;
type EntityArrayResponseType = HttpResponse<IJournal[]>;

@Injectable({ providedIn: 'root' })
export class JournalService {
  public resourceUrl = SERVER_API_URL + 'api/journals';

  constructor(protected http: HttpClient) {}

  create(journal: IJournal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journal);
    return this.http
      .post<IJournal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(journal: IJournal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journal);
    return this.http
      .put<IJournal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJournal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJournal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(journal: IJournal): IJournal {
    const copy: IJournal = Object.assign({}, journal, {
      journalDate: journal.journalDate && journal.journalDate.isValid() ? journal.journalDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.journalDate = res.body.journalDate ? moment(res.body.journalDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((journal: IJournal) => {
        journal.journalDate = journal.journalDate ? moment(journal.journalDate) : undefined;
      });
    }
    return res;
  }
}
