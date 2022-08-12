package se.main.gameloveservice;

import lombok.Builder;
import se.main.gameloveservice.game.GameDto;
import se.main.gameloveservice.player.PlayerDto;

public class UnloveGameDto extends LoveGameDto {

    @Builder(builderMethodName = "UnloveGameDtoBuilder")
    public UnloveGameDto(PlayerDto player, GameDto game) {
        super(player, game);
    }

}
