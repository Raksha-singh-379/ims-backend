import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PurchaseQuotationDetailComponent } from './purchase-quotation-detail.component';

describe('PurchaseQuotation Management Detail Component', () => {
  let comp: PurchaseQuotationDetailComponent;
  let fixture: ComponentFixture<PurchaseQuotationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PurchaseQuotationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ purchaseQuotation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PurchaseQuotationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PurchaseQuotationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load purchaseQuotation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.purchaseQuotation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
