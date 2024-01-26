import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class HelloService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  public sayHello(): Observable<string> {
    return this.http.get(`${this.apiServerUrl}/onyx/admin/hello`, {
      responseType: 'text',
    });
  }
}
