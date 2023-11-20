package com.example.demo.meetingroom;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingroomDto {
    private int id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int roomId;

    public MeetingroomDto toDto(Meetingroom m){
        return MeetingroomDto.builder()
                .id(m.getId())
                .title(m.getTitle())
                .startDate(m.getStartDate())
                .endDate(m.getEndDate())
                .roomId(m.getRoomId())
                .build();
    }
}
