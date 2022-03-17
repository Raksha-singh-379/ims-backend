import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransferDetailsApprovalsDetailComponent } from './transfer-details-approvals-detail.component';

describe('TransferDetailsApprovals Management Detail Component', () => {
  let comp: TransferDetailsApprovalsDetailComponent;
  let fixture: ComponentFixture<TransferDetailsApprovalsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransferDetailsApprovalsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transferDetailsApprovals: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransferDetailsApprovalsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransferDetailsApprovalsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transferDetailsApprovals on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transferDetailsApprovals).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
