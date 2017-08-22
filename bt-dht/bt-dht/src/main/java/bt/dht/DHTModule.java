package bt.dht;

import bt.module.ProtocolModule;
import bt.module.ServiceModule;
import bt.protocol.handler.PortMessageHandler;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @since 1.1
 */
public class DHTModule implements Module {

    private DHTConfig config;

    /**
     * @since 1.1
     */
    public DHTModule() {
        this.config = new DHTConfig();
    }

    /**
     * @since 1.1
     */
    public DHTModule(DHTConfig config) {
        this.config = config;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(DHTConfig.class).toInstance(config);

        ServiceModule.contributePeerSourceFactory(binder).addBinding().to(DHTPeerSourceFactory.class);
        ProtocolModule.contributeHandshakeHandler(binder).addBinding().to(DHTHandshakeHandler.class);
        ProtocolModule.contributeMessageHandler(binder).addBinding(PortMessageHandler.PORT_ID).to(PortMessageHandler.class);

        // this service contributes startup lifecycle bindings and should be instantiated eagerly
        binder.bind(DHTService.class).to(MldhtService.class).asEagerSingleton();
    }
}
