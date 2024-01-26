import { Component, OnInit } from '@angular/core';
import { HelloService } from '../hello.service';

@Component({
  selector: 'app-corpus',
  templateUrl: './corpus.component.html',
  styleUrls: ['./corpus.component.css'],
})
export class CorpusComponent implements OnInit {
  helloMessage: string = '';

  constructor(private helloService: HelloService) {}

  ngOnInit(): void {
    this.getHelloMessage();
  }

  getHelloMessage() {
    this.helloService.sayHello().subscribe(
      (response: string) => {
        this.helloMessage = response;
      },
      (error) => {
        console.error('Erreur lors de la récupération du message : ', error);
      }
    );
  }
}
