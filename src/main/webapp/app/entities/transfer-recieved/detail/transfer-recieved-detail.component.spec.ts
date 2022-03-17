import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransferRecievedDetailComponent } from './transfer-recieved-detail.component';

describe('TransferRecieved Management Detail Component', () => {
  let comp: TransferRecievedDetailComponent;
  let fixture: ComponentFixture<TransferRecievedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransferRecievedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transferRecieved: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransferRecievedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransferRecievedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transferRecieved on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transferRecieved).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
