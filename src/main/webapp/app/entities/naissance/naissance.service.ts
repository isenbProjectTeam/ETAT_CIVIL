import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Naissance } from './naissance.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class NaissanceService {

    private resourceUrl = 'api/naissances';
    private resourceSearchUrl = 'api/_search/naissances';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(naissance: Naissance): Observable<Naissance> {
        const copy: Naissance = Object.assign({}, naissance);
        copy.dateDeclaration = this.dateUtils
            .convertLocalDateToServer(naissance.dateDeclaration);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(naissance: Naissance): Observable<Naissance> {
        const copy: Naissance = Object.assign({}, naissance);
        copy.dateDeclaration = this.dateUtils
            .convertLocalDateToServer(naissance.dateDeclaration);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Naissance> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.dateDeclaration = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.dateDeclaration);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }


    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].dateDeclaration = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].dateDeclaration);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
