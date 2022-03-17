import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoodsRecivedDetailComponent } from './goods-recived-detail.component';

describe('GoodsRecived Management Detail Component', () => {
  let comp: GoodsRecivedDetailComponent;
  let fixture: ComponentFixture<GoodsRecivedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GoodsRecivedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ goodsRecived: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GoodsRecivedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GoodsRecivedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load goodsRecived on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.goodsRecived).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
