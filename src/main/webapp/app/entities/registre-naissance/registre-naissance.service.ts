import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RegistreNaissance } from './registre-naissance.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class RegistreNaissanceService {

    private resourceUrl = 'api/registre-naissances';
    private resourceSearchUrl = 'api/_search/registre-naissances';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(registreNaissance: RegistreNaissance): Observable<RegistreNaissance> {
        const copy: RegistreNaissance = Object.assign({}, registreNaissance);
        copy.annee = this.dateUtils
            .convertLocalDateToServer(registreNaissance.annee);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(registreNaissance: RegistreNaissance): Observable<RegistreNaissance> {
        const copy: RegistreNaissance = Object.assign({}, registreNaissance);
        copy.annee = this.dateUtils
            .convertLocalDateToServer(registreNaissance.annee);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RegistreNaissance> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.annee = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.annee);
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
            jsonResponse[i].annee = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].annee);
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
