package se.main.gameloveservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.main.gameloveservice.game.GameDto;
import se.main.gameloveservice.player.PlayerDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoveGameDto {
    @Valid
    @NotNull
    private PlayerDto player;

    @Valid
    @NotNull
    private GameDto game;
}
