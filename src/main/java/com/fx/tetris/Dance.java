package com.fx.tetris;

import com.fx.tetris.utils.FileUtils;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class Dance {

    Media media;
    //创建媒体播放器
    MediaPlayer mPlayer;
    //视图
    MediaView mView;

    public void start(){
        Director.isPlay = true;
        // 判断是在最上层
        Pane game = Director.getInstance().getGame();
        ObservableList<Node> children = game.getChildren();
        if (children.contains(mView)) {
            children.remove(mView);
            children.add(mView);
        }
        mView.setVisible(true);
        Director.running = false;
        //设置播放时间从 0 开始
        mPlayer.seek(Duration.ZERO);
        mPlayer.play();
    }


    public void init(){
        Director.isPlay = false;
        media = new Media(FileUtils.getResource("/mp4/8943ef6c52b91f30a506b7ce77c500c8.mp4"));
        mPlayer=new MediaPlayer(media);
        //创建媒体对象
        mPlayer.setAutoPlay(false);
        // 播放完成后执行
        mPlayer.setOnEndOfMedia(()->{
            Director.running = true;
            Director.isPlay = false;
            // 是否可见
            mView.setVisible(false);
        });

        //创建媒体播放视图
        mView=new MediaView(mPlayer);
        //设置媒体播放视图的宽度和高度
        mView.setFitWidth(Director.WIDTH);
        mView.setFitHeight(Director.HEIGHT);
        mView.setVisible(false);

        //添加到图形
        Pane game = Director.getInstance().getGame();
        game.getChildren().add(mView);
    }
}
