package pl.moja.wypozyczalnia.utils.converters;

import pl.moja.wypozyczalnia.database.models.Segment;
import pl.moja.wypozyczalnia.modelFx.SegmentFx;


public class ConverterSegment {

    public static SegmentFx convertToSegmentFx(Segment segment){
        SegmentFx segmentFx = new SegmentFx();
        segmentFx.setId(segment.getId());
        segmentFx.setName(segment.getName());
        return segmentFx;
    }
}
