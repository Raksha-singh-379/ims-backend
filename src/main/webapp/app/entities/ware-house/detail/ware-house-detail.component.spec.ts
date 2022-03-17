import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WareHouseDetailComponent } from './ware-house-detail.component';

describe('WareHouse Management Detail Component', () => {
  let comp: WareHouseDetailComponent;
  let fixture: ComponentFixture<WareHouseDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WareHouseDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ wareHouse: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(WareHouseDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WareHouseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load wareHouse on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.wareHouse).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
