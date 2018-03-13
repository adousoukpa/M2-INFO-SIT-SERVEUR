/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ServeurTestModule } from '../../../test.module';
import { ObstacleDetailComponent } from '../../../../../../main/webapp/app/entities/obstacle/obstacle-detail.component';
import { ObstacleService } from '../../../../../../main/webapp/app/entities/obstacle/obstacle.service';
import { Obstacle } from '../../../../../../main/webapp/app/entities/obstacle/obstacle.model';

describe('Component Tests', () => {

    describe('Obstacle Management Detail Component', () => {
        let comp: ObstacleDetailComponent;
        let fixture: ComponentFixture<ObstacleDetailComponent>;
        let service: ObstacleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServeurTestModule],
                declarations: [ObstacleDetailComponent],
                providers: [
                    ObstacleService
                ]
            })
            .overrideTemplate(ObstacleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ObstacleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObstacleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Obstacle('123')));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith('123');
                expect(comp.obstacle).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
