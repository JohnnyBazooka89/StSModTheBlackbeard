CREATE TABLE run(
    file_path varchar primary key,
    status varchar,
    error_message varchar,
    is_endless bool,
    left_too_early bool,
    character varchar,
    ascension varchar,
    host varchar,
    language varchar,
    victory bool,
    neow_bonus varchar,
    play_time int,
    time int,
    floor_reached int,
    score int,
    play_id varchar,
    local_time varchar,
    is_ascension_mode bool,
    circlet_count int,
    seed_played varchar,
    is_trial bool,
    campfire_rested int,
    gold int,
    is_daily bool,
    chose_seed bool,
    campfire_upgraded int,
    win_rate double precision,
    timestamp int,
    build_version varchar,
    purchased_purges int,
    player_experience int,
    is_beta bool
);

CREATE TABLE damage_taken(
    run_file_path varchar,
    enemies varchar,
    damage int,
    floor int,
    turns int,
    CONSTRAINT fk_damage_taken_run_file_path FOREIGN KEY(run_file_path) REFERENCES run(file_path)
);

CREATE TABLE killed_by(
    run_file_path varchar,
    enemy_id varchar,
    CONSTRAINT fk_killed_by_run_file_path FOREIGN KEY(run_file_path) REFERENCES run(file_path)
);

CREATE TABLE card_choice(
    run_file_path varchar,
    card_id varchar,
    picked boolean,
    CONSTRAINT fk_card_choice_run_file_path FOREIGN KEY(run_file_path) REFERENCES run(file_path)
);

CREATE TABLE master_deck(
    run_file_path varchar,
    card_id varchar,
    CONSTRAINT fk_card_choice_run_file_path FOREIGN KEY(run_file_path) REFERENCES run(file_path)
);

CREATE TABLE relic(
    run_file_path varchar,
    relic_id varchar,
    CONSTRAINT fk_card_choice_run_file_path FOREIGN KEY(run_file_path) REFERENCES run(file_path)
);

CREATE INDEX idx_run_ascension ON run(ascension);
CREATE INDEX idx_run_character ON run(character);
CREATE INDEX idx_damage_taken_enemies ON damage_taken(enemies);
CREATE INDEX idx_killed_by_enemy_id ON killed_by(enemy_id);
CREATE INDEX idx_card_choice_card_id ON card_choice(card_id);
CREATE INDEX idx_master_deck_card_id ON master_deck(card_id);
CREATE INDEX idx_relic_relic_id ON relic(relic_id);